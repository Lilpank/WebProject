<#import "parts/common.ftl" as c>
<@c.page>
    <form method="post" action="/" enctype="multipart/form-data">
        <div class="form-group">
            <div class="col-md-3 ">
                <label>Write the name</label>
            </div>
            <div class="form-group col-md-6">
                <input class="form-control" type="text" name="title" placeholder="Name film">
            </div>

            <div class="form-group col-md-6">
                <i>Genres: </i>
                <#list ["HORROR", "COMEDY", "THRILLER", "DOCUMENTARY", "DRAMA", "ADVENTURE", "WESTERN", "MUSICAL", "FANTASY"] as genre>
                    <div class="form-check form-check-inline">
                        <label><input type="checkbox" name="${genre}">${genre}</label>
                    </div>
                </#list>
            </div>
            <div class="form-group col-md-6">
                <div>
                    <label>Insert a picture</label>
                </div>
                <div class="custom-file">
                    <input type="file" class="custom-file-input" id="customFile" name="file">
                    <label class="custom-file-label" for="customFile">Choose file</label>
                </div>
            </div>
            <div class="form-group col-md-6">
                <textarea maxlength="500" class="form-control" name="description"
                          placeholder="description film"></textarea>
            </div>
            <div class="form-group col-md-6">
                <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                <div class="form-group col-md-6">
                    <button type="submit" class="btn btn-primary">Submit</button>
                </div>
            </div>
        </div>
    </form>
</@c.page>