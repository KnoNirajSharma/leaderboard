FROM node:13-alpine
WORKDIR /app
ENV PATH /app/node_modules/.bin:$PATH
COPY package.json /app/package.json
RUN npm install -g @ionic/cli
RUN npm install
COPY src /app
EXPOSE 8100
CMD ionic serve --address=0.0.0.0
