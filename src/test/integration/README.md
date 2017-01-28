# Advisors API Integration Tests

This directory contains files that run integration tests against the advisors API. To run the tests, use frisby.js. These libraries are required to run the integration tests:

* [jasmine npm](https://www.npmjs.com/package/jasmine-node)
* [frisby.js](http://frisbyjs.com)

To build the container:
    docker build -t advisors-tests .

Use this command to run the tests, all you need installed is docker:

    docker run --rm --name advisors-tests -v "$PWD":/usr/src/app:ro advisors-tests

Successfully passing all the tests with the command above would output this result:

![success_test](images/successful-test.png)

