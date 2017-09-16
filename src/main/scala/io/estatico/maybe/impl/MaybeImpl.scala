package io.estatico.maybe.impl

private[maybe] object MaybeImpl {

  type Maybe[A] = { type Repr = A ; type Tag = MaybeImpl.type }

  @inline def apply[A](x: A): Maybe[A] = x.asInstanceOf[Maybe[A]]
  @inline def applyM[M[_], A](mx: M[A]): M[Maybe[A]] = mx.asInstanceOf[M[Maybe[A]]]

  def toOption[A](ma: Maybe[A]): Option[A] = if (ma == null) None else Some(ma.asInstanceOf[A])

  def fromOption[A](oa: Option[A]): Maybe[A] = oa match {
    case None => apply(null).asInstanceOf[Maybe[A]]
    case Some(x) => apply(x)
  }

  object Just {
    def apply[A](x: A): Maybe[A] = {
      if (x == null) throw new IllegalArgumentException("Cannot pass `null` to Just")
      MaybeImpl(x)
    }

    def unapply[A](mx: Maybe[A]): Boolean = mx != null
  }

  type NixType = { type IsNix = NixTag }
  trait NixTag

  val Nix = null.asInstanceOf[NixType]
}
