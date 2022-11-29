FROM python:3.11-slim-buster


RUN echo "holas"
ENV PYTHONUNBUFFERED 1
ENV PYTHONDONTWRITEBYTECODE 1
RUN  pip install --upgrade pip
COPY ./stg/requirements.txt .
COPY ./stg/libs/ stg/libs
RUN pip install -r  ./requirements.txt

WORKDIR stg/libs/twobeatstg
RUN pip install -e .

WORKDIR /stg
