package com.github.javiersantos.appupdater.objects;

public class GitHub {
    private String gitHubUser;
    private String gitHubRepo;

    public GitHub(String gitHubUser, String gitHubRepo) {
        this.gitHubUser = gitHubUser;
        this.gitHubRepo = gitHubRepo;
    }

    public String getGitHubUser() {
        return gitHubUser;
    }

    public void setGitHubUser(String user) {
        this.gitHubUser = user;
    }

    public String getGitHubRepo() {
        return gitHubRepo;
    }

    public void setGitHubRepo(String repo) {
        this.gitHubRepo = repo;
    }

    public static Boolean isGitHubValid(GitHub gitHub) {
        if (gitHub == null || gitHub.getGitHubUser().length() == 0 || gitHub.getGitHubRepo().length() == 0) {
            return false;
        } else {
            return true;
        }
    }

}
