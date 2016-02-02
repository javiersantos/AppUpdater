package com.github.javiersantos.appupdater.objects;

public class GitHub {
    private String githubUser;
    private String githubRepo;

    public GitHub(String githubUser, String githubRepo) {
        checkGithub(githubUser, githubRepo);
        this.githubUser = githubUser;
        this.githubRepo = githubRepo;
    }

    public String getGithubUser() {
        return githubUser;
    }

    public void setGithubUser(String user) {
        this.githubUser = user;
    }

    public String getGithubRepo() {
        return githubRepo;
    }

    public void setGithubRepo(String repo) {
        this.githubRepo = repo;
    }

    private void checkGithub(String user, String repo) {
        if (user.length() == 0 || repo.length() == 0) {
            throw new IllegalArgumentException("GitHub user or repo is empty");
        }
    }

}
