package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/*import action.Action;
import action.BestListAction;
import action.BoardMainAction;
import action.BoardRegisterBoardContentAction;
import action.ContentAction;
import action.DeclarationAction;
import action.DeclarationCheckAction;
import action.DuplicateCheckAction;
import action.LikeCheckAction;
import action.ListAction;
import action.MemberRegisterAction;
import action.MemberRegisterProcAction;
import action.RecommandListAction;
import action.RecommandReadAction;
import action.RecommandUpdateAction;
import action.RecommandWriteAction;
import action.RecommandWriteProcAction;
import action.UpdateAction;
import action.UpdateProcAction;
import action.WriteAction;
import action.WriteProcAction;
import action.YoutuberRegisterAction;
import action.YoutuberRegisterProcAction;
import action.BoardMainAction;
import action.loginProcAction;
import action.logoutProcAction;
import action.mainAction;
import action.memberDeleteAction;
import action.memberDeleteProcAction;
import action.memberModifyAction;
import action.memberModifyProcAction;
import action.testAction;*/
import action.*;
import vo.ActionForward;



@WebServlet("*.oshi")
public class OshiFrontController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
  

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}

	
	
	protected void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.setCharacterEncoding("utf-8");
		
		String requestURI = request.getRequestURI();
		String contextPath = request.getContextPath();
		String command = requestURI.substring(contextPath.length());
		
		Action action = null;
		ActionForward forward = null;
		
		
		
		//url에 따른 분기
		switch (command) {
		case "/main.oshi":
			action = new MainAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		break;
		
		case "/memberRegister.oshi":
			action = new MemberRegisterAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		break;
			
		case "/memberRegisterProc.oshi":
			action = new MemberRegisterProcAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		break;
		
		case "/loginProc.oshi":
			action = new LoginProcAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		break;
		
		case "/logoutProc.oshi":
			action = new LogoutProcAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		break;
		
		case "/memberModify.oshi":
			action = new MemberModifyAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		break;
		
		case "/memberModifyProc.oshi":
			action = new MemberModifyProcAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		break;
		
		
		case "/memberDelete.oshi":
			action = new MemberDeleteAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		break;
		
		case "/memberDeleteProc.oshi":
			action = new MemberDeleteProcAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}			
		break;
		
	
		
		case "/individual_read.oshi":   //김동
			action = new ContentAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		break;
		
		case "/duplicateCheck.oshi":
			action = new DuplicateCheckAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		break;
		
		
		case "/likeCheck.oshi":
			action = new LikeCheckAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		break;
		
		
		case "/individual_write.oshi":    //김동
			action = new WriteAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		break;
		
		
		case "/writeProc.oshi":   //김동
			action = new WriteProcAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		break;
		
		case "/updateProc.oshi":   //김동
			action = new UpdateProcAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		break;
		
		case "/update.oshi":    //김동
			action = new UpdateAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		break;
		
		case "/boardMain.oshi":
			action = new BoardMainAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		break;
		
		
		//4.23 추가분
        case "/recommandUpdateProc.oshi":
           action = new RecommandUpdateProcAction();
           try {
              forward = action.execute(request, response);
           } catch (Exception e) {
              e.printStackTrace();
           }
           break;
           
        case "/recommandDelete.oshi":
           action = new RecommandDeleteAction();
           try {
              forward = action.execute(request, response);
           } catch (Exception e) {
              e.printStackTrace();
           }
           break;
           
        case "/adminMain.oshi":
           action = new AdminMainAction();
           try {
              forward = action.execute(request, response);
           } catch (Exception e) {
              e.printStackTrace();
           }
           break;
           
        case "/adminNotice.oshi":
           action = new AdminNoticeAction();
           try {
              forward = action.execute(request, response);
           } catch (Exception e) {
              e.printStackTrace();
           }
           break;
           
        case "/adminAuthority.oshi":
           action = new AdminAuthorityAction();
           try {
              forward = action.execute(request, response);
           } catch (Exception e) {
              e.printStackTrace();
           }
           break;
        case "/adminReport.oshi":
           action = new AdminReportAction();
           try {
              forward = action.execute(request, response);
           } catch (Exception e) {
              e.printStackTrace();
           }
           break;
        case "/adminBoardRegister.oshi":
           action = new AdminBoardRegisterAction();
           try {
              forward = action.execute(request, response);
           } catch (Exception e) {
              e.printStackTrace();
           }
           break;
		
           
        case "/deletePro.oshi":
            action = new DeleteProcAction();
            try {
               forward = action.execute(request, response);
            } catch (Exception e) {
               e.printStackTrace();
            }
            
         break;
         
         case "/delete.oshi":
            action = new DeleteAction();
            try {
               forward = action.execute(request, response);
            } catch (Exception e) {
               e.printStackTrace();
            }
            
         break;
		
		  case "/recommand.oshi":
		         action = new RecommandListAction();
		         try {
		            forward = action.execute(request, response);
		         } catch (Exception e) {
		            e.printStackTrace();
		         }
		      break;
		      //추천곡게시판-글쓰기
		      case "/recommandWrite.oshi":
		         action = new RecommandWriteAction();
		         try {
		            forward = action.execute(request, response);
		         } catch (Exception e) {
		            e.printStackTrace();
		         }
		      break;
		      //추천곡게시판-글쓰기2
		      case "/recommandWriteProc.oshi":
		         action = new RecommandWriteProcAction();
		         try {
		            forward = action.execute(request, response);
		         } catch (Exception e) {
		            e.printStackTrace();
		         }
		      break;
		      //추천곡게시판-읽기
		      case "/recommandRead.oshi":
		         action = new RecommandReadAction();
		         try {
		            forward = action.execute(request, response);
		         } catch (Exception e) {
		            e.printStackTrace();
		         }
		      break;
		      //추천곡게시판-수정
		      case "/recommandUpdate.oshi":
		         action = new RecommandUpdateAction();
		         try {
		            forward = action.execute(request, response);
		         } catch (Exception e) {
		            e.printStackTrace();
		         }
		      break;
		
		      
		      
		   // 개별 게시판 - 이노
		      case "/individual_board.oshi":
		         action = new ListAction();
		         try {
		            forward = action.execute(request, response);
		         } catch (Exception e) {
		            e.printStackTrace();
		         }
		         
		      break;

		// 개념글 - 이노
		      case "/individual_best_board.oshi":
		         action = new BestListAction();
		         try {
		            forward = action.execute(request, response);
		         } catch (Exception e) {
		            e.printStackTrace();
		         }
		         
		      break;
		      
		      // 관리자게시판에서 유투버 신청자 정보출력 - 이노
		      case "/youtuberRegister.oshi":
		         action = new YoutuberRegisterAction();
		         try {
		            forward = action.execute(request, response);
		         } catch (Exception e) {
		            e.printStackTrace();
		         }
		         
		      break;
		      
		      //유투버 신청 처리시 쓰는거
		      case "/youtuberRegisterProc.oshi":
		         action = new YoutuberRegisterProcAction();
		         try {
		            forward = action.execute(request, response);
		         } catch (Exception e) {
		            e.printStackTrace();
		         }
		         
		      break;
		      
		      
		   // 관리자게시판에서 관리자 신청자 정보출력 - 이노
		      case "/adminRegister.oshi":
		         action = new AdminRegisterAction();
		         try {
		            forward = action.execute(request, response);
		         } catch (Exception e) {
		            e.printStackTrace();
		         }
		         
		      break;
		      
		      //관리자 신청 처리시 쓰는거
		      case "/adminRegisterProc.oshi":
		         action = new AdminRegisterProcAction();
		         try {
		            forward = action.execute(request, response);
		         } catch (Exception e) {
		            e.printStackTrace();
		         }
		         
		      break;
		      
		      
		      
		      
		      
		      
		      case "/BoardInfoUpdate.oshi":
					action = new BoardInfoUpdateAction();
					try {
						forward = action.execute(request, response);
					} catch (Exception e) {
						e.printStackTrace();
					}
					
				break;
				
				
				//위에걸 실행 - 이노
				case "/BoardInfoUpdateProc.oshi":
					action = new BoardInfoUpdateProcAction();
					try {
						forward = action.execute(request, response);
					} catch (Exception e) {
						e.printStackTrace();
					}
					
				break;
				
				//공지목록보기 -이노
				case "/NoticeList.oshi":
					action = new NoticeListAction();
					try {
						forward = action.execute(request, response);
					} catch (Exception e) {
						e.printStackTrace();
					}
					
				break;
				
				//공지목록쓰기 -이노
				case "/NoticeWrite.oshi":
					action = new NoticeWriteAction();
					try {
						forward = action.execute(request, response);
					} catch (Exception e) {
						e.printStackTrace();
					}
					
				break;

				
				//공지목록쓰기 실행 -이노
				case "/NoticeWriteProc.oshi":
					action = new NoticeWriteProcAction();
					try {
						forward = action.execute(request, response);
					} catch (Exception e) {
						e.printStackTrace();
					}
					
				break;
				
				//공지글 보기 -이노
				case "/NoticeRead.oshi":
					action = new NoticeReadAction();
					try {
						forward = action.execute(request, response);
					} catch (Exception e) {
						e.printStackTrace();
					}
					
				break;


		      
		      
		      
		      
		      
		      
		      
		      
		      
		      
		      
		      
		      
		      
		      
		      
		      
		      
		      //신고 민우
		      case "/declaration.oshi":
		          action = new DeclarationAction();
		          try {
		             forward = action.execute(request, response);
		          } catch (Exception e) {
		             e.printStackTrace();
		          }
		          
		       break;   
		       
		       case "/declarationCheck.oshi":
		          action = new DeclarationCheckAction();
		          try {
		             forward = action.execute(request, response);
		          } catch (Exception e) {
		             e.printStackTrace();
		          }
		          
		       break;   
		     //민우(댓글 작성)
	             case "/insertCommentsCheck.oshi":
	                   action = new CommentsProcAction();
	                   try {
	                      forward = action.execute(request, response);
	                   } catch (Exception e) {
	                      e.printStackTrace();
	                   }
	                   
	          //민우(댓글 삭제)
	             case "/deleteCommentsProc.oshi":
	                   action = new DeleteCommentsProcAction();
	                   try {
	                      forward = action.execute(request, response);
	                   } catch (Exception e) {
	                      e.printStackTrace();
	                   }
	                       
	                break; 
		       //민우(즐겨찾기)
	             case "/favoriteCheck.oshi":
	                   action = new FavoriteAction();
	                   try {
	                      forward = action.execute(request, response);
	                   } catch (Exception e) {
	                      e.printStackTrace();
	                   }
	                       
	                break;   
		     //재완 (게시판 신청 게시판 뷰)
				case "/boardRegisterBoard.oshi":
					action = new BoardRegisterBoardAction();
					try {
						forward = action.execute(request, response);
					} catch (Exception e) {
						e.printStackTrace();
					}
					
				break;
				
				
				
				
				//재완 (게시판 신청 게시판 폼 뷰)
				case "/boardRegisterBoardForm.oshi":
					action = new BoardRegisterBoardFormAction();
					try {
						forward = action.execute(request, response);
					} catch (Exception e) {
						e.printStackTrace();
					}
					
				break;
				
				
				//재완(멤버즐겨찾기삭제)
				case "/deleteFav.oshi":
					action = new DeleteFavAction();
					try {
						forward = action.execute(request, response);
					} catch (Exception e) {
						e.printStackTrace();
					}
					
				break;
				
				
				//재완(게시판 신청 게시판 폼 실제처리)
				case "/boardRegisterBoardFormProc.oshi":
					action = new BoardRegisterBoardFormProcAction();
					try {
						forward = action.execute(request, response);
					} catch (Exception e) {
						e.printStackTrace();
					}
					
				break;
				
				//재완(게시판 신청 게시판 내용 보기)
				case "/boardRegisterBoardContent.oshi":
					action = new BoardRegisterBoardContentAction();
					try {
						forward = action.execute(request, response);
					} catch (Exception e) {
						e.printStackTrace();
					}
					
				break;
				
				//재완(게시판 신청 게시판 댓글 달기)
				case "/boardRegisterBoardContentCommentProc.oshi":
					action = new BoardRegisterBoardContentProcAction();
					try {
						forward = action.execute(request, response);
					} catch (Exception e) {
						e.printStackTrace();
					}
					
				break;

				//재완(게시판 신청 게시판 게시글 추천)
				case "/boardRegisterBoardRecommendProc.oshi":
					action = new BoardRegisterBoardRecommendProcAction();
					try {
						forward = action.execute(request, response);
					} catch (Exception e) {
						e.printStackTrace();
					}
					
				break;
				
				//재완(게시판 신청 게시판 게시글 추천)
				case "/boardRegisterBoardDeleteProc.oshi":
					action = new BoardRegisterBoardDeleteProcAction();
					try {
						forward = action.execute(request, response);
					} catch (Exception e) {
						e.printStackTrace();
					}
					
				break;
				
				//재완(게시판 신청 게시판 게시글 수정 폼)
				case "/boardRegisterBoardModify.oshi":
					action = new BoardRegisterBoardModifyAction();
					try {
						forward = action.execute(request, response);
					} catch (Exception e) {
						e.printStackTrace();
					}
					
				break;
				
				
				//재완(게시판 신청 게시판 게시글 수정)
				case "/boardRegisterBoardFormModifyProc.oshi":
					action = new BoardRegisterBoardModifyProcAction();
					try {
						forward = action.execute(request, response);
					} catch (Exception e) {
						e.printStackTrace();
					}
					
				break;
				
				
				//재완(댓글 삭제 기능)
				case "/boardRegisterBoardContentCommentDeleteProc.oshi":
					action = new BoardRegisterBoardContentCommentDeleteProc();
					try {
						forward = action.execute(request, response);
					} catch (Exception e) {
						e.printStackTrace();
					}
					
				break;
				
				

				//재완(관리자 게시판 신청 승인 각하 처리)
				case "/adminBoardRegisterProc.oshi":
					action = new AdminBoardRegisterProc();
					try {
						forward = action.execute(request, response);
					} catch (Exception e) {
						e.printStackTrace();
					}
					
				break;
				
				case "/recommandCommentsProc.oshi":
					action = new RecommandCommentsProcAction();
					try {
						forward = action.execute(request, response);
					} catch (Exception e) {
						e.printStackTrace();
					}
					
				break;
				
				case "/recommandDeleteCommentsProc.oshi":
					action = new RecommandDeleteCommentsProcAction();
					try {
						forward = action.execute(request, response);
					} catch (Exception e) {
						e.printStackTrace();
					}
					
				break;
				
				
				
		
		
		default:
			break;
		}
		
		
		//실제 포워딩, 리다이렉팅
		if(forward != null) {
			if(forward.isRedirect()) {
				response.sendRedirect(forward.getPath());
			}else {
				RequestDispatcher dispatcher = request.getRequestDispatcher(forward.getPath());
				dispatcher.forward(request, response);
			}
		}
		
	}
}
