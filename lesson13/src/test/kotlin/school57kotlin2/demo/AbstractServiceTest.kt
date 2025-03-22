package school57kotlin2.demo

import com.ninjasquad.springmockk.MockkBean
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import school57kotlin2.demo.client.BlackListClient
import school57kotlin2.demo.client.SanctionsClient

@ActiveProfiles("test")
@SpringBootTest
abstract class AbstractServiceTest {

    @MockkBean
    lateinit var sanctionsClient: SanctionsClient

    @MockkBean
    lateinit var blackListClient: BlackListClient
}