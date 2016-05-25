poisson.dist <- function(n=50, mu=9) {
  bound <- exp(-mu)
  y <- c()
  for (i in 1:50) {
    m <- 0
    mul <- 1
    while (mul > bound) {
      mul <- mul * unif()
      m <- m + 1
    }
    y <- c(y, m - 1)
  }
  y
}