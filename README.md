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

# Advanced/Restricted Filtering

For advanced filtering in a particular use case, it is required to search for posts not having a particular
custom field. In order to search for such posts, the standard filter keys are not sufficient, and needs to
be enabled by allowing more keys.

Do note that the effect of this change is only visible when an authenticated call is made, as per the WP-API
documentation.
 
A snippet containing the keys that you wish to use needs to be added to either your theme's `functions.php`
file, or WP-API's `plugin.php`:
   
    function my_allow_meta_query( $valid_vars ) {
    
            $valid_vars = array_merge( $valid_vars, array( 'meta_key', 'meta_value', 'meta_compare' ) );
            return $valid_vars;
    }
    add_filter( 'rest_query_vars', 'my_allow_meta_query' );
    