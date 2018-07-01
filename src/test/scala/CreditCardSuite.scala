import org.scalatest._

class CreditCardSuite extends FunSuite with Matchers {
  test("Creating a card without passing any number should generate a valid credit card") {
    CreditCard().isValid shouldBe true
  }

  test("Creating a card without passing any number should create a card of class CreditCard.Valid") {
    CreditCard() shouldBe a[CreditCard.Valid]
  }

  test("Creating a card manually by passing a valid number should produce a valid credit card") {
    val validNumber = CreditCard().number

    CreditCard(validNumber).isValid shouldBe true

    noException should be thrownBy CreditCard(validNumber).asInstanceOf[CreditCard.Valid]
  }

  test("Credit cards toString method should mention validity") {
    CreditCard("").toString.toLowerCase should include ("invalid")
    CreditCard().toString.toLowerCase should not include "invalid"
  }

  test("All these cards copied from freeformatter.com should be valid") {
    val fakeCards =
      Set(
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
        "6387887062135843",
      ).map(CreditCard)

    all(fakeCards.map(_.isValid)) shouldBe true
  }

  test("10k generated cards should all be valid") {
    val fakeCards = 1 to 10000 map (_ => CreditCard())

    all(fakeCards.map(_.isValid)) shouldBe true
  }
}
