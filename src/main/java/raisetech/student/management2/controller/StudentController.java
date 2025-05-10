
package raisetech.student.management2.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import raisetech.student.management2.data.MessageResponse;
import raisetech.student.management2.data.StudentsCourse;
import raisetech.student.management2.domain.StudentDetail;
import raisetech.student.management2.exception.BadRequestException;
//import raisetech.student.management2.exception.InvalidStudentIdException;
import raisetech.student.management2.exception.StudentNotFoundException;
import raisetech.student.management2.service.StudentService;

/**
 * 受講生の検索や登録、更新等を行うREST APIとして実行するコントローラ
 */

@Validated
@RestController
public class StudentController {

  private StudentService service;

  /**
   * コンストラクタ
   *
   * @param service
   * @param
   */
  @Autowired
  public StudentController(StudentService service) {
    this.service = service;
  }

  /**
   * 受講生詳細の一覧検索 全件検索を行う。条件指定は行わないもの。
   *
   * @return 受講生一覧（全件）
   */
  @Operation(summary = "受講生一覧検索", description = "受講生一覧を全件検索します",responses =
      {@ApiResponse(responseCode = "200", description = "一覧検索の成功", content = @Content(mediaType = "application/json")),
      })
  @GetMapping("/studentList")
  public List<StudentDetail> getStudentList() {

    return service.searchStudentList();

  }

  /**
   * 受講生詳細検索を行う IDに紐づく受講生の情報を取得する
   *
   * @param id 受講生ID
   * @return 受講生詳細
   */

  @Operation(summary = "受講生詳細検索", description = "受講生詳細を検索します", responses =
      {@ApiResponse(responseCode = "200",description = "idに応じた学生が検索される", content = @Content(mediaType = "application/json", schema = @Schema(implementation = StudentDetail.class))),
          @ApiResponse(responseCode = "404", description ="指定した学生は存在しません",content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
          @ApiResponse(responseCode = "400", description = "入力が不適切です" , content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),})
  @GetMapping("/student/{id}")
  public StudentDetail getStudent(@PathVariable String id) {

    // 400 Bad Request のチェック
    if (id == null || id.trim().isEmpty() || !id.matches("\\d{1,2}")) {
      throw new BadRequestException("IDは1～2桁の数字で指定してください");
    }

    // サービス層で受講生を検索
    StudentDetail student = service.searchStudent(id);

    // 404 Not Found のチェック
//    if (student == null) {
//      throw new StudentNotFoundException("指定されたIDの受講生は存在しません");
//    }

    return student;
  }
//難しい箇所:AIツールの使い方

  /**
   * 受講生コース検索を行う
   * @return 受講生コース一覧
   */
  @Operation(summary = "コース一覧検索", description = "コース一覧検索をします", responses =
      {@ApiResponse(responseCode = "200", description = "コース一覧が表示される", content = @Content(mediaType = "application/json", schema = @Schema(implementation = StudentsCourse.class))),
          @ApiResponse(responseCode = "500", description = "サーバーエラー", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
      })
  @GetMapping("/courseList")
  public List<StudentsCourse> getCourseList () {
    return service.searchCourseList();

  }


  /**
   * 受講生詳細の登録を行う
   * @param studentDetail 受講生詳細
   * @return 実行結果
   */
  @Operation(summary = "受講生登録", description = "受講生情報を登録します", responses =
      {
          @ApiResponse(responseCode = "200", description = "登録成功", content = @Content(mediaType = "application/json", schema = @Schema(implementation = StudentDetail.class))),
          @ApiResponse(responseCode = "400", description = "入力が不適切です", content = @Content(mediaType = "application/json" ,schema = @Schema(implementation = ErrorResponse.class))),
          @ApiResponse(responseCode = "500", description = "サーバーエラー", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
      })
  @PostMapping("/registerStudent")
  public ResponseEntity<StudentDetail> registerStudent (@RequestBody @Valid StudentDetail
      studentDetail){
    StudentDetail responseStudentDetail = service.registerStudent(studentDetail);
    return ResponseEntity.ok(responseStudentDetail);
  }

  /**
   * 受講生コースの追加します
   * @param studentsCourse 受講生コース情報
   * @return 実行結果
   */

  @Operation(summary = "受講生コース登録", description = "受講生コース情報を登録します", responses =
      {@ApiResponse(responseCode = "200", description = "登録成功", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponse.class))),
          @ApiResponse(responseCode = "400", description = "入力が不適切です", content = @Content(mediaType = "application/json" , schema = @Schema(implementation = ErrorResponse.class))),
          @ApiResponse(responseCode = "500", description = "サーバーエラー", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))})
  @PostMapping("/addCourse")
  public ResponseEntity<MessageResponse> registerStudentCourse (@RequestBody StudentsCourse
      studentsCourse){
    service.registerStudentCourse(studentsCourse);
    return ResponseEntity.ok(new MessageResponse("登録を実行しました"));
  }
  //下、レッスン33

  /**
   * 受講生詳細を更新します。
   * キャンセルフラグの更新もここで行う（論理削除）
   * @param studentDetail 受講生詳細
   * @return 実行結果
   */
  @Operation(summary = "受講生更新", description = "受講生情報を更新します", responses =
      {@ApiResponse(responseCode = "200", description = "更新成功", content = @Content(mediaType = "application/json" , schema = @Schema(implementation = MessageResponse.class))),
          @ApiResponse(responseCode = "400", description = "入力が不適切です", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
          @ApiResponse(responseCode = "500", description = "サーバーエラー", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))})
  @PutMapping("/updateStudent")
  public ResponseEntity<MessageResponse> updateStudent (@RequestBody @Valid StudentDetail studentDetail){

    service.updateStudent(studentDetail);
    System.out.println(

        studentDetail.getStudent().getName() + "さんが更新されました");


    return ResponseEntity.ok(new MessageResponse("更新を実行しました"));
  }

  /**
   * idからコース情報を取得します。
   * @param studentId 受講生番号
   * @return 実行結果
   */
  @GetMapping("/courseList/{studentId}")
  public List<StudentsCourse> getCourseList (@PathVariable @Size(min = 1, max = 10) Long studentId)
  {
    return service.searchCourseList();
    //引数消した
  }
  //対応するサービス層がないため有無を言わさず全件検索
}

