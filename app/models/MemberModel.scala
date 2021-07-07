package models

import play.api.data._
import javax.inject.Inject
import play.api.db._

// Member Class
case class Member(
    id: Int,
    firstName: String,
    lastName: String,
    indexNo: String,
    email: String
)

// Member Form Class
case class MemberForm(
    firstName: String,
    lastName: String,
    indexNo: String,
    email: String
)

class MemberModel @Inject() (
    db: Database
) {

  /*
    Methods
    -------

    getMemberDetails(): 
        Fetch all the member details 
    createMember(): 
        Create the member record 
    getMember(): 
        Fetch a particular member using id
    updateMember(): 
        Update the member details 
    deleteMember(): 
        Delete the record of the member using id

  */

  def getMemberDetails(): Seq[Member] = {

    // Databse connection
    val connection = db.getConnection()
    val stmt = connection.createStatement
    val query =
      "SELECT * FROM Member;"
    val resultSet = stmt.executeQuery(query)

    // Store details as member objects
    var members: Seq[Member] = Seq()

    while (resultSet.next()) {

      var member = new Member(
        id = resultSet.getInt("id"),
        firstName = resultSet.getString("firstname"),
        lastName = resultSet.getString("lastname"),
        indexNo = resultSet.getString("indexno"),
        email = resultSet.getString("email")
      )
      members = members :+ member
    }
    connection.close()

    // Return the sequence of member objects
    members

  }


  def createMember(memberForm: MemberForm): Unit = {

    // Store form details in variables
    var fname: String = memberForm.firstName
    var lname: String = memberForm.lastName
    var indexno: String = memberForm.indexNo
    var email: String = memberForm.email
    
    // Databse connection
    val connection = db.getConnection()
    val stmt = connection.createStatement
    val query =
      s"INSERT INTO Member (firstname, lastname, indexno, email) VALUES (\"$fname\", \"$lname\", \"$indexno\", \"$email\");"
    val resultSet = stmt.execute(query)
    connection.close()
  }


  def getMember(id: Int): Member = {

    // Databse connection
    val connection = db.getConnection()
    val stmt = connection.createStatement
    val query =
      s"SELECT * FROM Member WHERE id=$id LIMIT 0, 1;"
    val resultSet = stmt.executeQuery(query)

    // Initialize varibales to store data from database
    var firstName: String = ""
    var lastName: String = ""
    var indexNo: String = ""
    var email: String = ""

    // Store details in vriables
    while (resultSet.next()) {
        firstName = resultSet.getString("firstname")
        lastName = resultSet.getString("lastname")
        indexNo = resultSet.getString("indexno")
        email = resultSet.getString("email")
    }

    connection.close()

    // Store details as member object
    var member:Member = new Member(
      id = id,
      firstName = firstName,
      lastName = lastName,
      indexNo = indexNo,
      email = email
    )

    // Return the member object
    member

  }

  def updateMember(member: Member): Unit = {

    // Store form details in variables
    var id:Int = member.id
    var fname: String = member.firstName
    var lname: String = member.lastName
    var indexno: String = member.indexNo
    var email: String = member.email

    // Databse connection
    val connection = db.getConnection()
    val stmt = connection.createStatement
    val query =
      s"Update Member SET firstname=\"$fname\", lastname=\"$lname\", indexno=\"$indexno\", email=\"$email\" WHERE id=$id;"
    val resultSet = stmt.executeUpdate(query)
    connection.close()

  }


  def deleteMember(id: Int): Unit = {

    // Databse connection
    val connection = db.getConnection()
    val stmt = connection.createStatement
    val query =
      s"DELETE FROM Member WHERE id=$id;"
    val resultSet = stmt.executeUpdate(query)
    connection.close()

  }

}
