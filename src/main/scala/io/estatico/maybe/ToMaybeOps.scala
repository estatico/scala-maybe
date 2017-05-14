package io.estatico.maybe

object ToMaybeOps extends ToMaybeOps

trait ToMaybeOps {
  implicit def toMaybeOps[A](x: Maybe[A]): MaybeOps[A] = new MaybeOps[A](x)
}