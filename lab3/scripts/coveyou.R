twoe <- 2 ^ 30;
x.current <- floor(runif(1) * twoe / 4) * 4 + 2

coveyou <- function() {
  x.current <<- (x.current * (x.current + 1)) %% twoe
  x.current
}

unif <- function(use.custom) {
  if (use.custom) {
    coveyou() / twoe
  } else {
    runif(1)
  }
}