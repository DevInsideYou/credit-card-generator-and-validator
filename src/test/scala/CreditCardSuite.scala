import org.scalatest._
import org.scalatest.prop._

import org.scalacheck._

class CreditCardSuite extends FunSuite with Matchers with PropertyChecks {
  val genValid: Gen[CreditCard] =
    Arbitrary.arbitrary[Unit].map(_ => CreditCard())

  implicit val arbitraryValid: Arbitrary[CreditCard.Valid] =
    Arbitrary(genValid.map(_.asInstanceOf[CreditCard.Valid]))

  test("Creating a card without passing any number should generate a valid credit card") {
    forAll { validCard: CreditCard.Valid =>
      validCard.isValid shouldBe true
    }
  }

  test("Creating a card manually by passing a valid number should produce a valid credit card") {
    forAll { validCard: CreditCard.Valid =>
      val validNumber = validCard.number

      CreditCard(validNumber).isValid shouldBe true

      noException should be thrownBy CreditCard(validNumber).asInstanceOf[CreditCard.Valid]
    }
  }

  test("Credit cards toString method should mention validity") {
    CreditCard("").toString.toLowerCase should include("invalid")

    forAll { validCard: CreditCard.Valid =>
      validCard.toString.toLowerCase should not include "invalid"
    }
  }

  test("All these cards copied from freeformatter.com should be valid") {
    val fakeValidCards =
      Seq(
        "0604326448044080",
        "30166725723574",
        "30257046091021",
        "30294018909708",
        "341187902765570",
        "3541554640440604",
        "3542693324121525",
        "3589717201082460822",
        "36268386338793",
        "36631296369242",
        "36766377557818",
        "377560970646384",
        "378140783126020",
        "4026575583448348",
        "4071885695832931",
        "4091739759762839789",
        "4175001001348662",
        "4175002782178369",
        "4929749206271704",
        "5101460153519270",
        "5204344410052968",
        "5231960878190706",
        "5427922224180173",
        "5558772376417266",
        "5588250087285979",
        "5893046723149417",
        "5893505008915446",
        "6011062269562137775",
        "6011278148379643",
        "6011555484292906",
        "6370424233370023",
        "6380761773419647",
        "6387887062135843"
      ).map(CreditCard)

    val gen: Gen[CreditCard] = Gen.oneOf(fakeValidCards)

    forAll { validCard: CreditCard.Valid =>
      validCard.isValid shouldBe true
    }
  }
}
