package com.github.javiersantos.appupdater.objects

class GitHub(var gitHubUser: String,
             var gitHubRepo: String) {

    companion object {
        fun isGitHubValid(gitHub: GitHub?): Boolean {
            return gitHub != null && gitHub.gitHubUser.isNotEmpty() && gitHub.gitHubRepo.isNotEmpty()
        }
    }
}