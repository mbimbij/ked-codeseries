ifndef APPLICATION_NAME
$(error APPLICATION_NAME is not set)
endif

AWS_ACCOUNT_ID := $(shell aws sts get-caller-identity --query "Account" --output text)
S3_BUCKET_NAME=$(APPLICATION_NAME)-$(AWS_ACCOUNT_ID)-bucket

s3-bucket::
	aws cloudformation deploy    \
          --stack-name $(S3_BUCKET_NAME)   \
          --template-file s3-bucket/s3-bucket.yml   \
          --parameter-overrides     \
            BucketName=$(S3_BUCKET_NAME)

all: s3-bucket
	aws cloudformation package --template-file master-stack.yml --output-template packaged-templates/master-stack.yml --s3-bucket $(S3_BUCKET_NAME)
	envsubst < stack-parameters.json  > packaged-templates/stack-parameters.json
	aws cloudformation deploy --stack-name $(APPLICATION_NAME) --template-file packaged-templates/master-stack.yml --parameter-overrides file://packaged-templates/stack-parameters.json --capabilities CAPABILITY_NAMED_IAM
delete-s3-bucket:
	./stack-deletion/delete-stack-wait-termination.sh $(S3_BUCKET_STACK_NAME)