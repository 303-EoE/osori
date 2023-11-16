#EC2

resource "aws_instance" "osori-redis-master" {

  ami                   = "ami-09eb4311cbaecf89d"
  instance_type         = "t2.medium"
  #Public IP 할당
  associate_public_ip_address = true
  hibernation           = false
  key_name              = "osoriKey"
  monitoring            = false
  
  
  vpc_security_group_ids = [aws_security_group.default_ec2.id, aws_security_group.redis.id]
  
  root_block_device {
    delete_on_termination = true
    volume_size           = 8
    volume_type           = "gp2"
  }
  
  tags = {
    Name = "osori-redis-master"
  }

}

resource "aws_instance" "osori-redis-slave" {

  ami                   = "ami-09eb4311cbaecf89d"
  instance_type         = "t2.small"
  associate_public_ip_address = true
  hibernation           = false
  key_name              = "osoriKey"
  monitoring            = false
  
  vpc_security_group_ids = [aws_security_group.default_ec2.id, aws_security_group.redis.id]
  
  root_block_device {
    delete_on_termination = true
    volume_size           = 8
    volume_type           = "gp2"
  }
  
  tags = {
    Name = "osori-redis-slave"
  }

}

resource "aws_instance" "osori-mariadb" {

  ami                   = "ami-09eb4311cbaecf89d"
  instance_type         = "t2.medium"
  associate_public_ip_address = true
  hibernation           = false
  key_name              = "osoriKey"
  monitoring            = false
  
  vpc_security_group_ids = [aws_security_group.default_ec2.id, aws_security_group.mysql.id]
  
  root_block_device {
    delete_on_termination = true
    volume_size           = 8
    volume_type           = "gp2"
  }
  
  tags = {
    Name = "osori-mariadb"
  }

}
