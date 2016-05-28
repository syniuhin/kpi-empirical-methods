# Custom beta function implementation. Works well only for small 'a' and 'b'.
beta <- function(a, b, unif.custom=T) {
  y1 <- 1
  y2 <- 1
  while (y1 + y2 > 1) {
    y1 <- unif(unif.custom) ^ (1 / a)
    y2 <- unif(unif.custom) ^ (1 / b)
  }
  y1 / (y1 + y2)
}

# Custom implementation of F-distribution, works well only for small k1 and k2.
f.dist <- function(n=50, k1=7, k2=15, unif.custom=T, beta.custom=T) {
  if (beta.custom) {
    yb <- c()
    for (i in 1:n)
      yb <- c(yb, beta(k1/2, k2/2, unif.custom))
  } else {
    yb <- rbeta(n, k1/2, k2/2)
  }
  y <- sapply(yb, function(yi) { k2 * yi / (k1 * (1 - yi)) })
  y
}