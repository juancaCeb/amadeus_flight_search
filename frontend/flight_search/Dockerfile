FROM node:alpine

WORKDIR /flight_search_frontend

COPY package*.json .

RUN npm install 

COPY . .

RUN npm run build

EXPOSE 5173

CMD ["npm", "run", "dev"]

