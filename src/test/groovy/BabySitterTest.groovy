import spock.lang.*
import org.joda.time.format.*

class BabySitterTest extends Specification {
  def "When a baby sitter works from 5-6 PM, he or she should be paid \$12"() {
    given: "A baby sitter"
      def babySitter = new BabySitter()
      def isoDateTimeParser = ISODateTimeFormat.dateTimeParser()
    when: "The baby sitter works from 5-6 PM"
      def startTime = isoDateTimeParser.parseDateTime('2016-04-01T17:00:00-05')
      def endTime = isoDateTimeParser.parseDateTime('2016-04-01T18:00:00-05')
      def paymentDue = babySitter.calculatePaymentDue(startTime,endTime)
    then: "He or she should be paid \$12"
      paymentDue == 12
  }
}
