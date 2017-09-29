#Introduction

I'm really glad you're reading this, because it means that the effort of making this code open has been worthwhile.  We have a couple of points that you'll need to be aware of:

* If you find a security issue, please don't raise a request.  We’d rather you let us know directly by detailing the issue and emailing it to us at [SecureComms](mailto:secure.communicationsproject@dwp.gsi.gov.uk).  We guarantee to take a look.
* When wishing to raise a pull request we would be grateful if you could let us know what has changed and why the change is required.

##Submitting changes

Please send a GitHub Pull request to [SecureComms](mailto:secure.communicationsproject@dwp.gsi.gov.uk?Subject=Pull%20Request) with a clear list of what you've done (read more about [pull requests](https://help.github.com/articles/about-pull-requests/)).  When you send a pull request, we’d really appreciate it if you could include examples and use cases; we can always use more test coverage.
  
Please follow our coding conventions (below), make sure all of your commits are atomic (one feature per commit) and always write a clear message against your commit that fully describes the change.

    git commit -m "A summary of the commit, what’s changed and the impact."

##Coding conventions

Start reading our code and you'll get the hang of it.  We use [SonarLint](http://www.sonarlint.org/intellij/) & [Checkmarx](https://checkmarx.atlassian.net/wiki/display/KC/CxSAST+IntelliJ+Plugin) for immediate code quality and vulnerability review and our IDE is [IntelliJ](https://www.jetbrains.com/idea/).  All code follows the standard out-of-the-box code formatting rules.

This is open source software.  Consider the people who will read your code and make it look nice for them. Remember your code will be in the public domain and we don’t just want your code to be efficient, we’d like it to be elegant too.

##Testing

All good (and responsible) coding comes with a complete set of unit tests and thorough coverage.  

We use [Cucumber](https://cucumber.io/), [JUnit](http://junit.org/junit4/) and [Mockito](http://site.mockito.org/) to test our code.  Some of our tests haven't been included in the repo because they expose a little more information about our internal processes than we’d like to share; sorry about that.

We accept that you can't write for tests you can't see but if you're going to contribute, please write a full set of tests for the code you have changed and we'll take it from there.  

We're more than happy to look at any suggestions; a different perspective can be wholly refreshing!

##Communication

Once we get a request from you we'll firstly acknowledge receipt.

Then we’ll assess the content and purpose of your suggestion and let you know what we're going to do with it.  Please don't be offended if we can't accept it; we will try and explain the reasons for this decision if that's the case.

If we _can_ accept your contribution we'll raise a task on our backlog to implement the change.  Ultimately, your code may look a little different to the original submission once our standards, security concerns and internal formatting rules have been applied but it’s still your contribution.  

If we accept your code and it makes it to a release then we'll acknowledge your valuable contribution in the change log, as well as letting you know directly.


_`document reference : ContributionGuide.docx (1.1)`_