package io.estatico.maybe
package instances

import cats.{Monad, Show}

import scala.annotation.tailrec

trait MaybeInstances {

  implicit def showMaybe[A](implicit show: Show[A]): Show[Maybe[A]] = {
    _.cata("Nix", a => s"Just(${show.show(a)})")
  }

  implicit val monadMaybe: Monad[Maybe] = new Monad[Maybe] {

    override def pure[A](x: A): Maybe[A] = Just(x)

    override def flatMap[A, B](fa: Maybe[A])(f: A => Maybe[B]): Maybe[B] = fa.flatMap(f)

    @tailrec
    override def tailRecM[A, B](a: A)(f: A => Maybe[Either[A, B]]): Maybe[B] = {
      f(a).getOrNull match {
        case null => Nix
        case Right(x) => Maybe(x)
        case Left(y) => tailRecM(y)(f)
      }
    }
  }
}
