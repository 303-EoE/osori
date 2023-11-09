
provider "aws" {

#	profile = ""
	region = "ap-northeast-2"
}

resource "aws_s3_bucket" "osori-bucket" {
	bucket = "osori-bucket"


}

resource "aws_s3_bucket_public_access_block" "public-access"{

	bucket = aws_s3_bucket.osori-bucket.id

	block_public_acls	= false
	block_public_policy	= false
	ignore_public_acls	= false
	restrict_public_buckets	= false
}

