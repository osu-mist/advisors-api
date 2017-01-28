var config = require('./configuration')
var frisby = require('frisby');

var baseUrl = config.hostname + config.version + config.api

frisby.create('Get access token')
  .post(baseUrl + config.token_endpoint, {
    grant_type: "client_credentials",
    client_id: config.client_id,
    client_secret: config.client_secret
  })
  //Verifying expected outcomes
  .timeout(10000)
  .expectStatus(200)
  .expectHeaderContains('content-type', 'application/json')
  .afterJSON(function(json) {
    frisby.globalSetup({
      request: {
        headers: {'Authorization': 'Bearer ' + json.access_token, 'Content-Type': 'application/json'}
      }
    });

    testWithoutParams();
    testGetByID();
    testGetByONID();
    testGetInvalidID();
  })
.toss();


frisby.create('Test oauth access requirement')
  //Calling get method
  .get(baseUrl)
  //Verifying expected outcomes
  .timeout(10000)
  .expectStatus(401)
  .expectHeaderContains('content-type', 'application/json')
.toss();

function testWithoutParams() {
  testGet( '', 'without parameters', 400);
}

function testGetByONID() {
  testGet( '?id=' + config.onid, 'with onid', 200);
}

function testGetByID() {
  testGet( '?id=' + config.id, 'with id', 200);
}

function testGetInvalidID() {
  testGet( '?id=' + config.invalid, 'with invalid id', 200);
}

function testGet(params, message, code) {
  frisby.create('Get advisors: ' + message)
    .get(baseUrl + params)
    //Verifying expected outcomes
    .timeout(10000)
    .expectStatus(code)
    .expectHeaderContains('content-type', 'application/json')
  .toss();
}
