package team.uptech.dagger_vm_processor

import javax.inject.Singleton
import kotlin.reflect.KClass

@Retention(AnnotationRetention.SOURCE)
@Target(AnnotationTarget.ANNOTATION_CLASS)
annotation class ViewModelModule(
  vararg val viewModels: KClass<*>,
  val scope: KClass<out Annotation> = Singleton::class
)