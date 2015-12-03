# WP-API v2 Java Client

A Java client to version 2 of the WP REST API

(Currently coding against **WP REST API Version 2.0-beta8**)

See

* http://v2.wp-api.org/
* https://github.com/WP-API

# Current Development Requirements

* WordPress 4.3+ installation
* JSON Basic Authentication (0.1 currently used)

# TODO

* Add support for authentication providers such as OAuth. (Currently only basic authentication is used)

# Testing

## Live Testing

These tests are intended to run against a live WordPress installation.

### Configuration

To run against your local wordpress installation, it is required to have a YAML configuration file
available at: `${project}/src/test/resources/config/${hostname}-test.yaml` with the following structure:

    wordpress:
      username: "myUsername"
      password: "myPassword"
      baseUrl: "http://myhost"
    
    debug: "true"
    
This configuration must not be included in version control. _`*.yaml` is already included in the `.gitignore` file._

Please ensure that you do not commit hard-coded environment information.

## WireMock Testing

The WireMock tests are sanity tests that must be rigid with known data and expectations.

## Testing TODO's

* Run tests in a dockerized Wordpress install