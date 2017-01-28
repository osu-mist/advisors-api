FROM node:6.6.0

RUN npm install -g jasmine-node
RUN npm install --save-dev frisby


USER nobody:nogroup

WORKDIR /usr/src/app

CMD ["jasmine-node", "."]
