## Introduction ##
Screenwaker is a simple app designed to send monitors to sleep when they aren't displaying important or new information. It then wakes them back up again whenever that changes.

It was originally written because we have a monitor in the RiskAware office that was wasting energy. The screen displays our "Build Radiator"; a simple dashboard showing the state of the builds on our continuous integration server. In our office, most of the time the dashboard shows a field of green builds. At RiskAware, we believe in keeping the build "green" at all times and so if a test ever fails, or a build breaks for any reason, fixing it becomes our top priority.

Another convention we like to follow in our work is "no news is good news" and so wanted that screen to just turn off and only wake up if any of the builds are failing.

Screenwaker was born.