FROM ubuntu:latest
LABEL authors="konra"

ENTRYPOINT ["top", "-b"]