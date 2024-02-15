infra:
	docker-compose -f cicd/infra/docker-compose.yml down && docker-compose -f cicd/infra/docker-compose.yml up -d

cleanup:
	docker-compose -f cicd/infra/docker-compose.yml down