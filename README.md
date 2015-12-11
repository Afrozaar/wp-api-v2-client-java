# WP-API v2 Java Client

A Java client to version 2 of the WP REST API

(Currently coding against **WP REST API Version 2.0-beta8**)

See

* http://v2.wp-api.org/
* https://github.com/WP-API

# Current Development Requirements

* WordPress 4.3+ installation
* JSON Basic Authentication (0.1 currently used)

# Implemented

* Posts CRUD
* Post Meta CRUD
* Terms CRUD
* Taxonomy CRUD
* Post Terms CRUD
* Pages CRUD

# Not Yet Implemented

* Post Revisions
* Post Types
* Post Statuses
* Comments
* Users

# Basic Usage

## Instantiating and using a new client

    String baseUrl = "http://myhost";
    String username = "myUsename";
    String password = "myPassword";
    boolean debug = false;
    
    final Wordpress client = ClientFactory.fromConfig(ClientConfig.of(baseUrl, username, password, debug));
    
## Creating a new post
    
    final Post post = PostBuilder.aPost()
        .withTitle(TitleBuilder.aTitle().withRendered(expectedTitle).build())
        .withExcerpt(ExcerptBuilder.anExcerpt().withRendered(expectedExcerpt).build())
        .withContent(ContentBuilder.aContent().withRendered(expectedContent).build())
        .build();
    
    final Post createdPost = client.createPost(post, PostStatus.publish);

## Searching Posts

### Search Posts not having a particular Meta Key
#### Sample Code
    final PagedResponse<Post> response = client.search(SearchRequest.Builder.aSearchRequest(Post.class)
            .withUri(Request.POSTS)
            .withParam("filter[meta_key]", "baobab_indexed")
            .withParam("filter[meta_compare]", "NOT EXISTS") //RestTemplate takes care of escaping values ('space' -> '%20')
            .build());                
                
#### Equivalent Curl/httpie Request
    $ http --auth 'username:password' http://myhost/wp-json/wp/v2/posts?filter[meta_key]=baobab_indexed&filter[meta_compare]=NOT%20EXISTS
    
#### Search types

The client is flexible enough to build search requests of a particular type, if that type supports filtering.

    final PagedResponse<Media> tagResults = client.search(SearchRequest.Builder.aSearchRequest(Media.class)
        .withUri("/media")
        .withParam("filter[s]", "searchTerm")
        .build());

#### Available Filters
* See [WordPress Codex](https://codex.wordpress.org/Class_Reference/WP_Query) for more filter options
* Also See [Advanced/Restricted Filtering](#Advanced/Restricted%20Filtering) for configuring restricted options 

## More Usage Examples
* For more examples, see [`/src/test/java/com/afrozaar/wordpress/wpapi/v2/ClientLiveTest.java`]
(https://bitbucket.org/afrozaar/wp-api-v2-client-java/src/3af73cf5eb8177ecce04f61320f70d971ea478ac/src/test/java/com/afrozaar/wordpress/wpapi/v2/ClientLiveTest.java?at=develop)
which has all the tests for a live installation and thus has examples of how to use the client.

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

