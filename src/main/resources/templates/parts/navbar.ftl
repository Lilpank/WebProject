<#include "security.ftl">
<#import "login.ftl" as l>

<nav class="navbar navbar-dark bg-dark navbar-expand-sm">
    <div class="header container">
        <a class="navbar-brand" href="/">YourFilms</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent"
                aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>

        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                <li class="nav-item">
                    <a class="nav-link active" aria-current="page" href="/">Home</a>
                </li>
                <#if isAdmin>
                    <li class="nav-item">
                        <a class="nav-link" href="/addFilm">add film</a>
                    </li>
                </#if>
                <#if isAdmin>
                    <li class="nav-item">
                        <a class="nav-link" href="/user">User list</a>
                    </li>
                </#if>
            </ul>
        </div>

        <form method="get" action="/" class="d-flex searcher ml-sm-2">
            <div class="dropdown">
                <button class="btn btn-secondary dropdown-toggle" type="button" id="dropdownMenu2"
                        data-toggle="dropdown"
                        aria-haspopup="true" aria-expanded="false">
                    Genres
                </button>
                <div class="dropdown-menu" aria-labelledby="dropdownMenu2">
                    <#list ["HORROR", "COMEDY", "THRILLER", "DOCUMENTARY", "DRAMA", "ADVENTURE", "WESTERN", "MUSICAL", "FANTASY"] as genre>
                        <button class="dropdown-item" type="submit" name="genre" value="${genre}">${genre}</button>
                    </#list>
                </div>
            </div>
        </form>

        <div id="navbar-menu" class="collapse navbar-collapse">
            <form method="get" action="/" class="d-flex searcher ml-sm-2">
                <div id="fast-search" class="fast-search input-group mr-3">
                    <input type="text" name="filter" class="form-control mr-3" value="${filter!}"
                           placeholder="Search by name">
                    <span class="input-group-append">
                <button class="btn btn-outline-success" type="submit">Search</button>
                    </span>
                </div>
            </form>

            <ul id="accountMenu" class="navbar-nav ml-auto mr-3">
                <@l.logout></@l.logout>
            </ul>
            <div class="navbar-text">${name}</div>
        </div>
    </div>
    </div>
</nav>
