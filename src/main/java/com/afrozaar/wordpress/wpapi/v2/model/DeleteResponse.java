package com.afrozaar.wordpress.wpapi.v2.model;

public class DeleteResponse<T> {

    /*
{
  "deleted": true,
  "previous": {
    "id": 419,
    "count": 0,
    "description": "kAUst",
    "link": "http:\/\/docker.dev\/tag\/tka\/",
    "name": "TKA",
    "slug": "tka",
    "taxonomy": "post_tag",
    "meta": []
  }
}
*/

    private Boolean deleted;

    private T previous;

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public T getPrevious() {
        return previous;
    }

    public void setPrevious(T previous) {
        this.previous = previous;
    }

    public static <T> DeleteResponse<T> of(Boolean deleted, T previous){
        DeleteResponse<T> response = new DeleteResponse<>();
        response.setPrevious(previous);
        response.setDeleted(deleted);
        return response;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("DeleteRequest{");
        sb.append("deleted=").append(deleted);
        sb.append(", previous=").append(previous);
        sb.append('}');
        return sb.toString();
    }
}
