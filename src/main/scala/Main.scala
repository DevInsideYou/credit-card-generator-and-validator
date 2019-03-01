object Main {
  def main(args: Array[String]): Unit = {
    println("-" * 50)

    code(args)

    println("-" * 50)
  }

  private def code(args: Array[String]): Unit = {
    args
      .headOption // Option[String]
      .map(CreditCard) // Option[CreditCard]
      .map(println) // Option[Unit]
      .getOrElse(runDemo)
  }

  private def runDemo(): Unit = {
    val validCard: CreditCard.Valid =
      CreditCard()

    println(validCard)
    println(validCard.number)
    println(validCard.isValid)

    println()

    val invalidCard: CreditCard =
      CreditCard("1234567812345678") // change the check digit (the last digit) to zero to make it a valid card

    println(invalidCard)
    println(invalidCard.number)
    println(invalidCard.isValid)

    println()

    val fakeCards = 1 to 10000 map (_ => CreditCard())

    println(fakeCards.forall(_.isValid))

    val moreFakeCards =
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
        "6387887062135843"
      ).map(CreditCard)

    val (valid, invalid) =
      moreFakeCards.partition(_.isValid)

    if (invalid.nonEmpty) {
      println()
      invalid foreach println
    }

    println()

    println("You can also pass in the credit card number as a command line arugment like this: run 5893505008915446")
  }
}
