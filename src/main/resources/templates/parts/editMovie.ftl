<#include "security.ftl">
<#macro editMovie title user>
    <#if isAdmin>
        <form action="${title}/${user}" method="get">
            <button type="submit" class="btn btn-outline-primary">Edit film</button>
        </form>
    </#if>
</#macro>

<#macro deleteFilm title>
    <#if isAdmin>
        <form action="${title}/delete" method="get">
            <button type="submit" class="btn btn-outline-primary">Delete film</button>
        </form>
    </#if>
</#macro>


