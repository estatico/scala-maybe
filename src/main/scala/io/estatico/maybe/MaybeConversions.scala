package io.estatico.maybe

trait MaybeConversions {
  implicit def nixToMaybe[A](nix: impl.MaybeImpl.NixType): Maybe[A] = nix.asInstanceOf[Maybe[A]]
}
