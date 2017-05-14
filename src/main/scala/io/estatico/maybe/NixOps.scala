package io.estatico.maybe

final class NixOps(val repr: impl.MaybeImpl.NixType) extends AnyVal {
  def apply[A]: Maybe[A] = impl.MaybeImpl.Nix.asInstanceOf[Maybe[A]]
}
