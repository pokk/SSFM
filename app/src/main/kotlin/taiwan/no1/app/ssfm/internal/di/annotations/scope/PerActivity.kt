package taiwan.no1.app.ssfm.internal.di.annotations.scope

/**
 * A scoping annotation to permit objects whose lifetime should conform to the life of the activity to be
 * memorized in the correct component.
 *
 * @author  jieyi
 * @since   6/8/17
 */
@javax.inject.Scope
@Retention
annotation class PerActivity