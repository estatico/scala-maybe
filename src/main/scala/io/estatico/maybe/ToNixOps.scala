package io.estatico.maybe

object ToNixOps extends ToNixOps

trait ToNixOps {
  implicit def toNixOps(x: impl.MaybeImpl.NixType): NixOps = new NixOps(x)
}
