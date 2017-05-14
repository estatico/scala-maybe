package io.estatico.maybe

import org.scalatest._
import org.scalatest.prop.PropertyChecks
import org.scalacheck.{Arbitrary, Gen}

class MaybeUnitTest
  extends PropSpec
  with PropertyChecks
  with Matchers
  with ArbitraryInstances {

  property("semantically equivalent to Option") (forAll { ox: Option[Int] =>
    val x = if (ox.isEmpty) null else ox.get
    val mx = Maybe(x)
    mx.cata(
      ox shouldEqual None,
      y => ox shouldEqual Some(y)
    )
  })

  property("isomorphism exists between Maybe and Option") {
    forAll { ox: Option[Int] => Maybe.fromOption(ox).toOption shouldEqual ox }
    forAll { mx: Maybe[Int] => Maybe.fromOption(mx.toOption) shouldEqual mx }
  }

  property("Nix.isEmpty") {
    (Nix == null) shouldEqual true
    val nix = Nix[Int]
    (nix == null) shouldEqual true
    nix.isEmpty shouldEqual true
    nix.nonEmpty shouldEqual false
  }

  property("Just(x).nonEmpty") (forAll { x: Int =>
    val mx = Just(x)
    (mx != null) shouldEqual true
    mx shouldEqual x
    mx.isEmpty shouldEqual false
    mx.nonEmpty shouldEqual true
  })

  property("Maybe.map") (forAll { (mx: Maybe[Int], y: Int) =>
    val actual = mx.map(_ + y)
    val expected = if (mx.isEmpty) Nix else Maybe(mx.unsafeGet + y)
    actual shouldEqual expected
  })

  property("Maybe.flatMap") (forAll { (mx: Maybe[Int], my: Maybe[Int]) =>
    val actual = for {
      x <- mx
      y <- my
    } yield x + y
    val expected = if (mx.isEmpty || my.isEmpty) Nix else mx.unsafeGet + my.unsafeGet
    actual shouldEqual expected
  })

  property("Maybe.orElse") (forAll { (mx: Maybe[Int], my: Maybe[Int]) =>
    val actual = mx.orElse(my)
    val expected = (mx, my) match {
      case (Nix, Nix) => Nix
      case (Just(x), Nix) => x
      case (Nix, Just(y)) => y
      case (Just(x), Just(_)) => x
    }
    actual shouldEqual expected
  })
}

trait ArbitraryInstances {

  implicit def arbMaybe[A](implicit arb: Arbitrary[A]): Arbitrary[Maybe[A]] = Arbitrary(
    implicitly[Arbitrary[Boolean]].arbitrary.flatMap {
      case false => Gen.const(Nix)
      case true => implicitly[Arbitrary[A]].arbitrary.map(Just(_))
    }
  )
}
