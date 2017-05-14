package io.estatico.maybe

final class MaybeOps[A](val repr: Maybe[A]) extends AnyVal {

  def isEmpty: Boolean = repr == null

  def nonEmpty: Boolean = repr != null

  def unsafeCoerceGet: A = get

  def getOrNull: A with AnyRef = (if (isEmpty) null else repr).asInstanceOf[A with AnyRef]

  def unsafeGet: A = if (nonEmpty) get else throw new NoSuchElementException("Maybe.unsafeGet")

  def toOption: Option[A] = if (isEmpty) None else Some(get)

  def cata[B](ifNix: => B, ifJust: A => B): B = if (isEmpty) ifNix else ifJust(get)

  def map[B](f: A => B): Maybe[B] = if (isEmpty) Nix else Maybe(f(get))

  def flatMap[B](f: A => Maybe[B]): Maybe[B] = if (isEmpty) Nix else f(get)

  def up[AA >: A]: Maybe[AA] = repr.asInstanceOf[Maybe[AA]]

  def orElse[AA >: A](m: Maybe[AA]): Maybe[AA] = if (isEmpty) m else up

  private[this] def get: A = repr.asInstanceOf[A]
}
