#1

EXIST_BLUE=$(sudo docker-compose -p osori-store-blue -f /home/ubuntu/workspace/docker-compose.store.blue.yaml ps | grep Up)

if [ -z "$EXIST_BLUE" ]; then
        sudo docker-compose -p osori-store-blue -f /home/ubuntu/workspace/docker-compose.store.blue.yaml pull
        sudo docker-compose -p osori-store-blue -f /home/ubuntu/workspace/docker-compose.store.blue.yaml up -d
        BEFORE_COLOR="green"
        AFTER_COLOR="blue"
        BEFORE_PORT_1=8232
        AFTER_PORT_1=8230
        BEFORE_PORT_2=8233
        AFTER_PORT_2=8231
else
        sudo docker-compose -p osori-store-green -f /home/ubuntu/workspace/docker-compose.store.green.yaml pull
        sudo docker-compose -p osori-store-green -f /home/ubuntu/workspace/docker-compose.store.green.yaml up -d
        BEFORE_COLOR="blue"
        AFTER_COLOR="green"
        BEFORE_PORT_1=8230
        AFTER_PORT_1=8232
        BEFORE_PORT_2=8233
        AFTER_PORT_2=8231
fi

echo "${AFTER_COLOR} server up (port:${AFTER_PORT_1})"
echo "${AFTER_COLOR} server up (port:${AFTER_PORT_2})"

#2
for cnt in {1..10}
do
        echo "서버1 응답 확인중(${cnt}/10)";
        UP=$(curl -s http://localhost:${AFTER_PORT_1}/server/check)
        if [ -z "${UP}" ]
                then
                        sleep 5
                        continue
                else
                        break
        fi
done

if [ $cnt -eq 10 ]
        then
                echo "서버1이 정상적으로 구동되지 않았습니다."
                exit 1
fi

for cnt in {1..10}
do
        echo "서버2 응답 확인중(${cnt}/10)";
        UP=$(curl -s http://localhost:${AFTER_PORT_2}/server/check)
        if [ -z "${UP}" ]
                then
                        sleep 5
                        continue
                else
                        break
        fi
done

if [ $cnt -eq 10 ]
        then
                echo "서버2가 정상적으로 구동되지 않았습니다."
                exit 1
fi

# 3
sed -i "s/${BEFORE_PORT_1}/${AFTER_PORT_1}/" /etc/nginx/sites-available/default
sed -i "s/${BEFORE_PORT_2}/${AFTER_PORT_2}/" /etc/nginx/sites-available/default
sudo nginx -s reload
echo "Deploy Completed!!"


# 4
echo "$BEFORE_COLOR server down(port:${BEFORE_PORT_1})"
echo "$BEFORE_COLOR server down(port:${BEFORE_PORT_2})"
sudo docker-compose -p osori-store-${BEFORE_COLOR} -f /home/ubuntu/workspace/docker-compose.store.${BEFORE_COLOR}.yaml down

# 5
sudo docker image prune -f