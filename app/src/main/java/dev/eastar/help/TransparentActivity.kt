package dev.eastar.help

import android.app.AlertDialog
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_FORWARD_RESULT
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class TransparentActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val content = intent.getStringExtra("content") ?: "" +
        "버스에 아끼는 우산을 두고 내렸어요.\n" +
        "어떻게 하죠.\n" +
        "버스번호는 기억나는데\n" +
        "하~~~ 힘드네요.."

        val mbtiDescriptions = arrayOf(
            "INTJ: 전략가, 독립적, 분석적",
            "INTP: 논리적 사색가, 분석적, 창의적",
            "ENTJ: 지휘관, 리더십 강함, 결단력",
            "ENTP: 토론가, 창의적, 변화를 좋아함",
            "INFJ: 옹호자, 사려 깊고 직관적",
            "INFP: 중재자, 이상주의자, 공감능력 강함",
            "ENFJ: 선도자, 사교적, 지도력",
            "ENFP: 활동가, 창의적, 긍정적",
            "ISTJ: 청렴결백한 논리주의자, 책임감 강함",
            "ISFJ: 용감한 수호자, 신뢰성 높음, 헌신적",
            "ESTJ: 엄격한 관리자, 실용적, 지도력",
            "ESFJ: 사교적인 외교관, 공감력 강함, 사람을 중요시",
            "ISTP: 만능 재주꾼, 문제 해결사, 실용적",
            "ISFP: 호기심 많은 예술가, 유연하고 수용적",
            "ESTP: 모험을 즐기는 기업가, 활동적, 문제 해결사",
            "ESFP: 자유로운 영혼의 연예인, 활기차고 긍정적"
        )

        // AlertDialog Builder 사용
        AlertDialog.Builder(this)
            .setTitle("답변 유형을 선택하세요")
            .setItems(
                arrayOf(
                    "동철님 스타일",
                    "동진님 스타일",
                    "그 밖에 다른 스타일..."
                )
            ) { dialog, which ->
                when (which) {
                    0 -> {
                        println("ESFJ")
                        onPrompt(content, "ESFJ")
                    }

                    1 -> {
                        println("INTP")
                        onPrompt(content, "ESFJ")
                    }

                    2 -> {
                        AlertDialog.Builder(this)
                            .setTitle("답변 유형을 선택하세요")
                            .setItems(mbtiDescriptions) { dialog, which ->
                                println(mbtiDescriptions[which].take(4))
                                onPrompt(content, mbtiDescriptions[which].take(4))
                            }
                            .setCancelable(false).show()
                    }
                }
            }.setCancelable(false).show()
    }

    private fun onPrompt(content: String, mbti: String) {
        lifecycleScope.launch {
            val response = prompt(content, mbti)
            println(response)
            AlertDialog.Builder(this@TransparentActivity)
                .setTitle("다음과 같이 답변을 완성 했어요 적용 해 볼까요?")
                .setMessage(response)
                .setPositiveButton("적용") { dialog, which ->
                    setResult(RESULT_OK, intent.putExtra("response", response))
                }
                .setNegativeButton("취소") { dialog, which ->
                    setResult(RESULT_CANCELED)
                }.setNeutralButton("다시") { dialog, which ->
                    startActivity(Intent(this@TransparentActivity, TransparentActivity::class.java).putExtra("content", content).setFlags(FLAG_ACTIVITY_FORWARD_RESULT))
                }
                .setCancelable(false)
                .setOnDismissListener {
                    finish()
                }
                .show()
        }
    }
}
