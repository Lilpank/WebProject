<#import "parts/common.ftl" as c>
<@c.page>
    <form method="post" action="/addFilm" enctype="multipart/form-data">
        <div class="form-group">
            <div class="col-md-3 ">
                <label>Write the name</label>
            </div>
            <div class="form-group col-md-6">
                <input type="text" class="form-control ${(titleError??)?string('is-invalid', '')}"
                       value="<#if film??>${film.title}</#if>" name="title" placeholder="Name film"/>
                <#if titleError??>
                    <div class="invalid-feedback">
                        ${titleError!}
                    </div>
                </#if>
            </div>
            <div class="form-group col-md-6">
                <i>Genres: </i>
                <#list ["HORROR", "COMEDY", "THRILLER", "DOCUMENTARY", "DRAMA", "ADVENTURE", "WESTERN", "MUSICAL", "FANTASY"] as genre>
                    <div class="form-check form-check-inline">
                        <label>
                            <input type="checkbox" value="" name="${genre}">
                            ${genre!}
                        </label>
                    </div>
                </#list>
            </div>
            <div class="form-group col-md-6">
                <div>
                    <label>Insert a picture</label>
                </div>
                <div class="custom-file">
                    <input type="file" class="custom-file-input" id="validatedCustomFile"
                           name="file" value="<#if film??>${film.filename!}</#if>">
                    <label class="custom-file-label" for="validatedCustomFile">Choose file</label>
                </div>
            </div>
            <div class="form-group col-md-6">
                <textarea maxlength="500" class="form-control ${(descriptionError??)?string('is-invalid', '')}"
                          name="description" value="<#if film??>${film.description}</#if>"
                          placeholder="description film"></textarea>
                <#if descriptionError??>
                    <div class="invalid-feedback">
                        ${descriptionError!}
                    </div>
                </#if>
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