include infra.env

ifndef APPLICATION_NAME
$(error APPLICATION_NAME is not set)
endif

all: s3-bucket ssh-key-pair
	$(eval export BASE_AMI_ID = $(shell aws ssm get-parameters --names /aws/service/canonical/ubuntu/server/20.04/stable/current/amd64/hvm/ebs-gp2/ami-id --query 'Parameters[0].[Value]' --output text))
	$(eval export USER_DATA_BASE_64 = $(shell base64 -w0 ec2-deployment/user-data.sh))
	aws cloudformation package --template-file master-stack.yml --output-template packaged-templates/master-stack.yml --s3-bucket $(S3_BUCKET_NAME)
	envsubst < stack-parameters.json  > packaged-templates/stack-parameters.json
	aws cloudformation deploy --stack-name $(APPLICATION_NAME) --template-file packaged-templates/master-stack.yml --parameter-overrides file://packaged-templates/stack-parameters.json --capabilities CAPABILITY_NAMED_IAM
delete-all:
	$(MAKE) -j2 delete-s3-bucket delete-ssh-key-pair
	./stack-deletion/delete-stack-wait-termination.sh $(APPLICATION_NAME)

AWS_ACCOUNT_ID := $(shell aws sts get-caller-identity --query "Account" --output text)
S3_BUCKET_NAME=$(APPLICATION_NAME)-$(AWS_ACCOUNT_ID)-bucket
s3-bucket::
	aws cloudformation deploy    \
          --stack-name $(S3_BUCKET_NAME)   \
          --template-file s3-bucket/s3-bucket.yml   \
          --parameter-overrides     \
            BucketName=$(S3_BUCKET_NAME)
delete-s3-bucket:
	./stack-deletion/delete-stack-wait-termination.sh $(S3_BUCKET_NAME)


ssh-key-pair::
	./ssh-key-pair/create-ssh-key-pair.sh $(SSH_KEY_NAME) $(SSH_KEY_PATH)
delete-ssh-key-pair:
	aws ec2 delete-key-pair --key-name $(SSH_KEY_NAME)
