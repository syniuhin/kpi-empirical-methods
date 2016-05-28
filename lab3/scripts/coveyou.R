twoe <- 2 ^ 30;
x.current <- floor(runif(1) * twoe / 4) * 4 + 2

# Method from Knuth's book, actually working quite poorly but was the best
# of all that I implemented in previous labs.
coveyou <- function() {
  x.current <<- (x.current * (x.current + 1)) %% twoe
  x.current
}

# Generates "uniformly" distributed values either by coveyou or by system
# default method.
unif <- function(use.custom) {
  if (use.custom) {
    coveyou() / twoe
  } else {
    runif(1)
  }
}