#Securit Group

resource "aws_security_group" "default_ec2"{
  name         = "default_ec2"
  description  = "default_ec2"
  vpc_id       = "vpc-0ffe081109519b96a"
  
  ingress {
    from_port    = 22
    to_port      = 22
    protocol     = "tcp"
    cidr_blocks  = ["0.0.0.0/0"]
  }
  
  ingress {
    from_port    = 80
    to_port      = 80
    protocol     = "tcp"
    cidr_blocks  = ["0.0.0.0/0"]
  }
  
  ingress {
    from_port    = 443
    to_port      = 443
    protocol     = "tcp"
    cidr_blocks  = ["0.0.0.0/0"]
  }
  
  egress {
    from_port    = 0
    to_port      = 0
    protocol     = "-1"
    cidr_blocks  = ["0.0.0.0/0"]
  }

  tags = {
    Name = "default_ec2"
  }

}

resource "aws_security_group" "redis" {

  name         = "redis"
  description  = "redis"
  vpc_id       = "vpc-0ffe081109519b96a"
  
  ingress {
    from_port    = 6379
    to_port      = 6379
    protocol     = "tcp"
    cidr_blocks  = ["0.0.0.0/0"]
  }
}

resource "aws_security_group" "mysql" {

  name         = "mysql"
  description  = "mysql"
  vpc_id       = "vpc-0ffe081109519b96a"
  
  ingress {
    from_port    = 3306
    to_port      = 3306
    protocol     = "tcp"
    cidr_blocks  = ["0.0.0.0/0"]
  }

}
