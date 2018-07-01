sealed trait CreditCard extends Any with Product with Serializable {
  import CreditCard._

  def number: String

  final def isValid: Boolean =
    isInstanceOf[Valid]

  final def isNotValid: Boolean =
    !isValid

  final override def toString: String =
    if(isNotValid) {
      val invalid = Console.RED + "Invalid" + Console.RESET

      s"""$invalid credit card number "$number""""
    }
    else {
      val valid = Console.GREEN + "Valid" + Console.RESET
      val (payload, checkDigit) = split(number)

      s"""$valid credit card number "$number" with payload "$payload" and check digit "$checkDigit""""
    }
}

object CreditCard extends (String => CreditCard) with (() => CreditCard) {
  final case class   Valid private (number: String) extends AnyVal with CreditCard

  object Valid {
    private[CreditCard] def apply(number: String): Valid =
      new Valid(number)
  }

  final case class Invalid private (number: String) extends AnyVal with CreditCard

  object Invalid {
    private[CreditCard] def apply(number: String): Invalid =
      new Invalid(number)
  }

  override def apply(number: String): CreditCard =
    if(isValid(number))
      Valid(number)
    else
      Invalid(number)

  private val CheckDigitLength = 1
  private val MinimumLength    = 13
  private val MaximumLength    = 19

  private def isValid(number: String): Boolean =
    number != null                                           &&
    number.nonEmpty                                          &&
    number.forall(Character.isDigit)                         &&
    (MinimumLength to MaximumLength).contains(number.length) &&
    doesMathCheckOut(number)

  private def doesMathCheckOut(number: String): Boolean = {
    val (payload, checkDigit) = split(number)
    val sum = luhn(payload) + checkDigit

    sum % 10 == 0
  }

  private def luhn(payload: String): Int =
    payload
      .reverse               // String
      .map(_.toString.toInt) // IndexedSeq[Int]
      .zipWithIndex          // IndexedSeq[(Int = Digit, Int = ZeroBasedIndex)] // first element has index zero
      .map {
        case (digit, index) =>
          if(index % 2 == 0)
            digit * 2
          else
            digit
      }                      // IndexedSeq[Int]
      .map { number =>
        if(number > 9)
          number - 9
        else
          number
      }                      // IndexedSeq[Int = Digit]
      .sum

  private def split(number: String): (String, Int) = {
    val payload    = number.dropRight(CheckDigitLength)
    val checkDigit = number.takeRight(CheckDigitLength).toInt
    // or          = number.last.toString.toInt

    payload -> checkDigit
  }

  override def apply(): Valid =
    Valid(generatedNumber)

  private def generatedNumber: String = {
    val payload: String = {
      import scala.util.Random

      val length: Int = {
        val min: Int = MinimumLength - CheckDigitLength // 12
        val max: Int = MaximumLength - CheckDigitLength // 18

        min + Random.nextInt((max - min) + 1) // 12 to 18
      // 12 + 0      until  (     6      + 1)
      }

      def randomDigit: Int =
        Random.nextInt(10) // 0 to 9

      (1 to length)            // Range.Inclusive
        .map(_ => randomDigit) // IndexedSeq[Int]
        .mkString              // String
    }

    val checkDigit: Int =
      (10 - (luhn(payload) % 10)) % 10

    val number: String =
      payload + checkDigit

    if(isValid(number))
      number
    else
      // $COVERAGE-OFF$
      sys.error(s"Bug: generated an invalid number: $number")
      // $COVERAGE-ON$

    // Instead of "manually throwing an exception
    // we also could have used the ensuring method like this:
    //
    // number.ensuring(isValid _)
    //
    // but I wanted to demonstrate the COVERAGE flags
  }
}
