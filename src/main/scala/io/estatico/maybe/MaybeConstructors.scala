package io.estatico.maybe

object MaybeConstructors extends MaybeConstructors

trait MaybeConstructors {
  type Maybe[A] = impl.MaybeImpl.Maybe[A]
  val Maybe = impl.MaybeImpl
  val Just = impl.MaybeImpl.Just
  val Nix = impl.MaybeImpl.Nix
}