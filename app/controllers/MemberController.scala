package controllers

import javax.inject._
import models.{Member, MemberForm, MemberModel}
import play.api.db._
import play.api.mvc._
import play.api.i18n.I18nSupport
import java.lang.ProcessBuilder.Redirect


@Singleton
class MemberController @Inject() (
    db: Database,
    controllerComponents: ControllerComponents
) extends AbstractController(controllerComponents)
    with I18nSupport {

  /*
    Methods
    -------

    index(): 
        Render the view contains all the member CRUD operations 
    memberCreateFormPost(): 
        Get member details from the index view and send to member model
    memberUpdatePost(): 
        Get the member detail from member model and send to index view
    memberUpdateFormPost(): 
        Get the member update details from index view and send to member model
    memberDeletePost(): 
        Get the id of the member to be deleted from index view and send to member model

  */

  // Initialize the member model
  val model = new MemberModel(db)


  def index() = Action { implicit request: Request[AnyContent] =>

    // Get all the member details from model
    val members: Seq[Member] = model.getMemberDetails()

    // Initialize the update member details as null
    var updateMember:Member = new Member(
      id = 0,
      firstName = "",
      lastName = "",
      indexNo = "",
      email = ""
    )

    // Send the member details to view
    Ok(views.html.index(members,updateMember))
  }


  def memberCreateFormPost(
      id: String,
      fname: String,
      lname: String,
      indexno: String,
      email: String
  ) = Action { implicit request =>

    // Initialize member form object using the create form details
    var memberForm = new MemberForm(
      firstName = fname,
      lastName = lname,
      indexNo = indexno,
      email = email
    )

    // Send the member form object to the model
    val create = model.createMember(memberForm)

    // Redirect to index page
    Redirect("/")
  }


  def memberUpdatePost(id:String)= Action { implicit request =>

    // Create member object using the member details to be updated
    val updateMember:Member = model.getMember(id.toInt)

    // Get all the member details from model
    var members: Seq[Member] = model.getMemberDetails()

    //Send the update member details to view
    Ok(views.html.index(members,updateMember))
  }

  def memberUpdateFormPost(
      id:String,
      fname: String,
      lname: String,
      indexno: String,
      email: String
  ) = Action { implicit request =>

    // Initialize member object using the update form details
    var member = new Member(
      id = id.toInt,
      firstName = fname,
      lastName = lname,
      indexNo = indexno,
      email = email
    )

    // Send the member update details to be model
    val update = model.updateMember(member)
    
    // Redirect to index page
    Redirect("/")
  }

  def memberDeletePost(id:String)= Action { implicit request =>
    
    // Send the member id to be deleted to the model
    val delete = model.deleteMember(id.toInt)
    
    // Redirect to index page
    Redirect("/")
  }

}
