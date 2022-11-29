FROM python:3.11-slim-buster

ENV PYTHONUNBUFFERED 1
ENV PYTHONDONTWRITEBYTECODE 1

COPY ./stg/requirements.txt .
RUN pip install -r  ./requirements.txt

WORKDIR stg

ENTRYPOINT python ./main.py