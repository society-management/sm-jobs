FROM openjdk:8-jre-alpine3.9

ENV USER=sm-jobs \
    GROUP=sm-jobs \
    USER_ID=65535 \
    GROUP_ID=65535 \
    HOME=/home/sm-jobs

RUN mkdir -p $HOME/.ssh && \
    addgroup --gid ${GROUP_ID} ${GROUP} && \
    adduser --disabled-password --gecos "" --uid ${USER_ID} --ingroup ${GROUP} -H ${USER} && \
    chmod 700 $HOME/.ssh

COPY target/ ${HOME}

RUN chown -R ${USER}:${GROUP} ${HOME}

WORKDIR /home/sm-jobs

USER sm-jobs

EXPOSE 9000 9009 3306 32769