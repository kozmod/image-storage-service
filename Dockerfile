FROM gradle:latest
LABEL stage=builder
WORKDIR /app
COPY . ./
RUN gradle build

FROM java:latest
LABEL maintainer="Kozmo"
WORKDIR /app
ADD --from=0 /app .
EXPOSE 8080
CMD ["./main"]